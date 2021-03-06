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
package com.hello2morrow.sonarplugin.metric;

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public class SonargraphDerivedMetrics implements Metrics {

  public static final Metric<Serializable> HIGHEST_ACD = new Metric.Builder("sonargraph_highest_acd", "Highest ACD", Metric.ValueType.FLOAT)
    .setDescription("The highest ACD of all build units").setDirection(Metric.DIRECTION_WORST).setQualitative(true).setDomain(SonargraphSimpleMetrics.DOMAIN_SONARGRAPH).create();

  public static final Metric<Serializable> HIGHEST_RELATIVE_ACD = new Metric.Builder("sonargraph_highest_relative_acd", "Highest Relative ACD", Metric.ValueType.FLOAT)
    .setDescription("The highest Relative ACD of all build units").setDirection(Metric.DIRECTION_WORST).setQualitative(true).setDomain(SonargraphSimpleMetrics.DOMAIN_SONARGRAPH)
    .create();

  public static final Metric<Serializable> HIGHEST_NCCD = new Metric.Builder("sonargraph_highest_nccd", "Highest NCCD", Metric.ValueType.FLOAT)
    .setDescription("The highest NCCD of all build units").setDirection(Metric.DIRECTION_WORST).setQualitative(true).setDomain(SonargraphSimpleMetrics.DOMAIN_SONARGRAPH).create();

  public static final Metric<Serializable> BIGGEST_CYCLE_GROUP = new Metric.Builder("sonargraph_biggest_cycle_group", "Biggest Cycle Group", Metric.ValueType.INT)
    .setDescription("Number of Packages in Biggest Cycle Group").setDirection(Metric.DIRECTION_WORST).setQualitative(true).setDomain(SonargraphSimpleMetrics.DOMAIN_SONARGRAPH)
    .create();

  public static final Metric<Serializable> CYCLIC_PACKAGES_PERCENT = new Metric.Builder("sonargraph_cyclic_packages_percent", "Percentage of Cyclic Packages",
    Metric.ValueType.PERCENT).setDescription("Percentage of Cyclically Coupled Packages").setDirection(Metric.DIRECTION_WORST).setQualitative(true)
    .setDomain(SonargraphSimpleMetrics.DOMAIN_SONARGRAPH).create();

  public static final Metric<Serializable> RELATIVE_CYCLICITY = new Metric.Builder("sonargraph_relative_cyclicity", "Relative Cyclicity", Metric.ValueType.PERCENT)
    .setDescription("Relative Cyclicity on Package Level").setDirection(Metric.DIRECTION_WORST).setQualitative(true).setDomain(SonargraphSimpleMetrics.DOMAIN_SONARGRAPH).create();

  public static final Metric<Serializable> UNASSIGNED_TYPES_PERCENT = new Metric.Builder("sonargraph_unassigned_types_percent", "Percentage of Unassigned Types",
    Metric.ValueType.PERCENT).setDescription("Percentage of Types not Assigned to any Architectural Artifacts").setDirection(Metric.DIRECTION_WORST).setQualitative(true)
    .setDomain(SonargraphSimpleMetrics.DOMAIN_SONARGRAPH).create();

  public static final Metric<Serializable> VIOLATING_TYPES_PERCENT = new Metric.Builder("sonargraph_violating_types_percent", "Percentage of Violating Types",
    Metric.ValueType.PERCENT).setDescription("Percentage of Types with Outgoing Architecture Violations").setDirection(Metric.DIRECTION_WORST).setQualitative(true)
    .setDomain(SonargraphSimpleMetrics.DOMAIN_SONARGRAPH).create();

  @Override
  public List<Metric> getMetrics() {
    List<Metric<Serializable>> metrics = Arrays.asList(HIGHEST_ACD, HIGHEST_RELATIVE_ACD, HIGHEST_NCCD, BIGGEST_CYCLE_GROUP, CYCLIC_PACKAGES_PERCENT, RELATIVE_CYCLICITY,
      UNASSIGNED_TYPES_PERCENT, VIOLATING_TYPES_PERCENT);
    List<Metric> newMetrics = new ArrayList<>();
    newMetrics.addAll(metrics);
    return newMetrics;
  }
}
