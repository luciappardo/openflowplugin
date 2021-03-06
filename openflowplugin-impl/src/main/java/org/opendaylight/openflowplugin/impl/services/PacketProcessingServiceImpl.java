/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.openflowplugin.impl.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;
import java.math.BigInteger;
import java.util.concurrent.Future;
import org.opendaylight.openflowplugin.api.openflow.device.DeviceContext;
import org.opendaylight.openflowplugin.api.openflow.device.RequestContextStack;
import org.opendaylight.openflowplugin.api.openflow.device.Xid;
import org.opendaylight.openflowplugin.openflow.md.core.sal.convertor.PacketOutConvertor;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.PacketOutInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.ConnectionCookie;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInput;
import org.opendaylight.yangtools.yang.common.RpcResult;

public class PacketProcessingServiceImpl extends CommonService implements PacketProcessingService {

    public PacketProcessingServiceImpl(final RequestContextStack requestContextStack, final DeviceContext deviceContext) {
        super(requestContextStack, deviceContext);
    }

    @Override
    public Future<RpcResult<Void>> transmitPacket(final TransmitPacketInput input) {

        return handleServiceCall(PRIMARY_CONNECTION, new Function<DataCrate<Void>, ListenableFuture<RpcResult<Void>>>() {

            @Override
            public ListenableFuture<RpcResult<Void>> apply(DataCrate<Void> data) {
                final Xid xid = data.getRequestContext().getXid();
                final PacketOutInput message = PacketOutConvertor.toPacketOutInput(input, version, xid.getValue(),
                        datapathId);

                BigInteger connectionID = PRIMARY_CONNECTION;
                final ConnectionCookie connectionCookie = input.getConnectionCookie();
                if (connectionCookie != null && connectionCookie.getValue() != null) {
                    connectionID = BigInteger.valueOf(connectionCookie.getValue());
                }

                ListenableFuture<RpcResult<Void>> rpcResultListenableFuture = JdkFutureAdapters.listenInPoolThread(provideConnectionAdapter(connectionID).packetOut(message));
                return Futures.transform(rpcResultListenableFuture, new AsyncFunction<RpcResult<Void>, RpcResult<Void>>() {
                    @Override
                    public ListenableFuture<RpcResult<Void>> apply(RpcResult<Void> rpcResult) throws Exception {
                        if (! rpcResult.isSuccessful()) {
                            return Futures.immediateFuture(rpcResult);
                        } else {
                            return Futures.immediateCancelledFuture();
                        }
                    }
                });
            }
        });

    }
}
