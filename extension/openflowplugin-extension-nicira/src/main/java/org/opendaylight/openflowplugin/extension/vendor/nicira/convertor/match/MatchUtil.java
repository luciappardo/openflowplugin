/**
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.openflowplugin.extension.vendor.nicira.convertor.match;

import java.util.HashSet;
import java.util.Set;

import org.opendaylight.openflowjava.nx.api.NiciraConstants;
import org.opendaylight.openflowplugin.extension.api.GroupingResolver;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.ExperimenterId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MatchField;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.OxmClassBase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MatchField;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entries.grouping.MatchEntry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entries.grouping.MatchEntryBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entry.value.grouping.MatchEntryValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.general.rev140714.general.extension.grouping.Extension;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxAugMatchNodesNodeTableFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxAugMatchNotifPacketIn;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxAugMatchNotifSwitchFlowRemoved;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxAugMatchNotifUpdateFlowStats;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxAugMatchRpcAddFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxAugMatchRpcRemoveFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxAugMatchRpcUpdateFlowOriginal;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxAugMatchRpcUpdateFlowUpdated;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmNxArpShaGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmNxArpThaGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmNxRegGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmNxTunIdGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmNxTunIpv4DstGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmNxTunIpv4SrcGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmOfArpOpGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmOfArpSpaGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmOfArpTpaGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmOfEthDstGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmOfEthSrcGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmOfEthTypeGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmNxNspGrouping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.match.rev140714.NxmNxNsiGrouping;
import org.opendaylight.yangtools.yang.binding.Augmentation;

/**
 * @author msunal
 *
 */
public class MatchUtil {

    private final static Set<Class<? extends Augmentation<Extension>>> augmentationsOfExtension = new HashSet<>();
    public final static GroupingResolver<NxmNxRegGrouping, Extension> regResolver = new GroupingResolver<>(
            NxmNxRegGrouping.class);
    public final static GroupingResolver<NxmNxTunIdGrouping, Extension> tunIdResolver = new GroupingResolver<>(
            NxmNxTunIdGrouping.class);
    public final static GroupingResolver<NxmNxArpShaGrouping, Extension> arpShaResolver = new GroupingResolver<>(
            NxmNxArpShaGrouping.class);
    public final static GroupingResolver<NxmNxArpThaGrouping, Extension> arpThaResolver = new GroupingResolver<>(
            NxmNxArpThaGrouping.class);
    public final static GroupingResolver<NxmOfArpOpGrouping, Extension> arpOpResolver = new GroupingResolver<>(
            NxmOfArpOpGrouping.class);
    public final static GroupingResolver<NxmOfArpSpaGrouping, Extension> arpSpaResolver = new GroupingResolver<>(
            NxmOfArpSpaGrouping.class);
    public final static GroupingResolver<NxmOfArpTpaGrouping, Extension> arpTpaResolver = new GroupingResolver<>(
            NxmOfArpTpaGrouping.class);
    public final static GroupingResolver<NxmNxTunIpv4DstGrouping, Extension> tunIpv4DstResolver = new GroupingResolver<>(
            NxmNxTunIpv4DstGrouping.class);
    public final static GroupingResolver<NxmNxTunIpv4SrcGrouping, Extension> tunIpv4SrcResolver = new GroupingResolver<>(
            NxmNxTunIpv4SrcGrouping.class);
    public final static GroupingResolver<NxmOfEthDstGrouping, Extension> ethDstResolver = new GroupingResolver<>(
            NxmOfEthDstGrouping.class);
    public final static GroupingResolver<NxmOfEthSrcGrouping, Extension> ethSrcResolver = new GroupingResolver<>(
            NxmOfEthSrcGrouping.class);
    public final static GroupingResolver<NxmOfEthTypeGrouping, Extension> ethTypeResolver = new GroupingResolver<>(
            NxmOfEthTypeGrouping.class);
    public final static GroupingResolver<NxmNxNsiGrouping, Extension> nsiResolver = new GroupingResolver<>(
            NxmNxNsiGrouping.class);
    public final static GroupingResolver<NxmNxNspGrouping, Extension> nspResolver = new GroupingResolver<>(
            NxmNxNspGrouping.class);

    static {
        augmentationsOfExtension.add(NxAugMatchRpcAddFlow.class);
        augmentationsOfExtension.add(NxAugMatchRpcRemoveFlow.class);
        augmentationsOfExtension.add(NxAugMatchRpcUpdateFlowOriginal.class);
        augmentationsOfExtension.add(NxAugMatchRpcUpdateFlowUpdated.class);
        augmentationsOfExtension.add(NxAugMatchNodesNodeTableFlow.class);
        augmentationsOfExtension.add(NxAugMatchNotifSwitchFlowRemoved.class);
        augmentationsOfExtension.add(NxAugMatchNotifPacketIn.class);
        augmentationsOfExtension.add(NxAugMatchNotifUpdateFlowStats.class);
        regResolver.setAugmentations(augmentationsOfExtension);
        tunIdResolver.setAugmentations(augmentationsOfExtension);
        arpShaResolver.setAugmentations(augmentationsOfExtension);
        arpThaResolver.setAugmentations(augmentationsOfExtension);
        arpOpResolver.setAugmentations(augmentationsOfExtension);
        arpSpaResolver.setAugmentations(augmentationsOfExtension);
        arpTpaResolver.setAugmentations(augmentationsOfExtension);
        tunIpv4DstResolver.setAugmentations(augmentationsOfExtension);
        tunIpv4SrcResolver.setAugmentations(augmentationsOfExtension);
        ethDstResolver.setAugmentations(augmentationsOfExtension);
        ethSrcResolver.setAugmentations(augmentationsOfExtension);
        ethTypeResolver.setAugmentations(augmentationsOfExtension);
        nspResolver.setAugmentations(augmentationsOfExtension);
        nsiResolver.setAugmentations(augmentationsOfExtension);

    }

    public static MatchEntryBuilder createDefaultMatchEntryBuilder(Class<? extends MatchField> matchField,
                                                                   Class<? extends OxmClassBase> oxmClass,
                                                                   MatchEntryValue matchEntryValue){
        MatchEntryBuilder matchEntryBuilder = new MatchEntryBuilder();
        matchEntryBuilder.setHasMask(false);
        matchEntryBuilder.setOxmMatchField(matchField);
        matchEntryBuilder.setOxmClass(oxmClass);
        matchEntryBuilder.setMatchEntryValue(matchEntryValue);
        return matchEntryBuilder;
    }

}
