package com.naqi.center.server.conn;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 编码器解码器工厂
 * 
 * @author hanfeng
 * 
 */
public class ClientCodecFactory implements ProtocolCodecFactory {
	private final ProtocolDecoder decoder;
	private final ProtocolEncoder encoder;

	public ClientCodecFactory() {
		this.decoder = new ClientDecoder();
		this.encoder = new ClientEncoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
