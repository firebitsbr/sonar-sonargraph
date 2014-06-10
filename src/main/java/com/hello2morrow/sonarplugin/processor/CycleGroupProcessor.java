/*
 * Sonar Sonargraph Plugin
 * Copyright (C) 2009, 2010, 2011 hello2morrow GmbH
 * mailto: info AT hello2morrow DOT com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hello2morrow.sonarplugin.processor;

import com.hello2morrow.sonarplugin.foundation.SonargraphPluginBase;
import com.hello2morrow.sonarplugin.foundation.Utilities;
import com.hello2morrow.sonarplugin.persistence.PersistenceUtilities;
import com.hello2morrow.sonarplugin.xsd.ReportContext;
import com.hello2morrow.sonarplugin.xsd.XsdAttributeRoot;
import com.hello2morrow.sonarplugin.xsd.XsdCycleGroup;
import com.hello2morrow.sonarplugin.xsd.XsdCycleGroups;
import com.hello2morrow.sonarplugin.xsd.XsdCyclePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issuable.IssueBuilder;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.JavaPackage;
import org.sonar.api.resources.Resource;
import org.sonar.api.rules.ActiveRule;

import java.util.ArrayList;
import java.util.List;

public class CycleGroupProcessor implements IProcessor {

  private final SensorContext sensorContext;
  private final RulesProfile rulesProfile;
  private static final Logger LOG = LoggerFactory.getLogger(CycleGroupProcessor.class);
  private double cyclicity = 0;
  private double biggestCycleGroupSize = 0;
  private double cyclicPackages = 0;
  private final ResourcePerspectives perspectives;

  public CycleGroupProcessor(final RulesProfile rulesProfile, final SensorContext sensorContext, final ResourcePerspectives perspectives) {
    this.rulesProfile = rulesProfile;
    this.sensorContext = sensorContext;
    this.perspectives = perspectives;
  }

  @Override
  public void process(ReportContext report, XsdAttributeRoot buildUnit) {
    XsdCycleGroups cycleGroups = report.getCycleGroups();

    for (XsdCycleGroup group : cycleGroups.getCycleGroup()) {
      if ("Physical package".equals(group.getNamedElementGroup())
        && PersistenceUtilities.getBuildUnitName(group).equals(Utilities.getBuildUnitName(buildUnit.getName()))) {
        int groupSize = group.getCyclePath().size();
        cyclicPackages += groupSize;
        cyclicity += groupSize * groupSize;
        if (groupSize > biggestCycleGroupSize) {
          biggestCycleGroupSize = groupSize;
        }
        handlePackageCycleGroup(group);
      }
    }
  }

  public double getCyclicity() {
    return cyclicity;
  }

  public double getBiggestCycleGroupSize() {
    return biggestCycleGroupSize;
  }

  public double getCyclicPackages() {
    return cyclicPackages;
  }

  @SuppressWarnings({"unchecked"})
  private void handlePackageCycleGroup(XsdCycleGroup group) {
    ActiveRule rule = rulesProfile.getActiveRule(SonargraphPluginBase.PLUGIN_KEY,
      SonargraphPluginBase.CYCLE_GROUP_RULE_KEY);
    if (rule == null) {
      return;
    }

    List<Resource<JavaPackage>> packages = new ArrayList<Resource<JavaPackage>>();
    for (XsdCyclePath pathElement : group.getCyclePath()) {
      String fqName = pathElement.getParent();
      Resource<JavaPackage> javaPackage = sensorContext.getResource(new JavaPackage(fqName));

      if (javaPackage == null) {
        LOG.error("Cannot obtain resource " + fqName);
      } else {
        packages.add(javaPackage);
      }
    }

    for (Resource<JavaPackage> jPackage : packages) {

      Issuable issuable = perspectives.as(Issuable.class, jPackage);
      IssueBuilder issueBuilder = issuable.newIssueBuilder();
      issueBuilder.severity(rule.getSeverity().toString()).ruleKey(rule.getRule().ruleKey());

      List<Resource<JavaPackage>> tempPackages = new ArrayList<Resource<JavaPackage>>(packages);
      tempPackages.remove(jPackage);
      StringBuffer buffer = new StringBuffer();
      buffer.append("Package participates in a cycle group");

      boolean first = true;
      for (Resource<JavaPackage> tPackage : tempPackages) {
        if (first) {
          buffer.append(" with package(s): ").append(tPackage.getName());
          first = false;
        } else {
          buffer.append(", ").append(tPackage.getName());
        }
      }
      issueBuilder.message(buffer.toString());
      issuable.addIssue(issueBuilder.build());
    }
  }
}
