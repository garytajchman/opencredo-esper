/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencredo.esper.integration;

import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;

public class MessageContext {
	private final Message<?> message;
	private final MessageChannel channel;
	private final IntegrationOperation operation;
	private final boolean sent;
	
	public MessageContext(Message<?> message, MessageChannel channel, IntegrationOperation operation) {
		super();
		this.message = message;
		this.channel = channel;
		this.operation = operation;
		this.sent = false;
	}
	
	public MessageContext(Message<?> message, MessageChannel channel, boolean sent) {
		super();
		this.message = message;
		this.channel = channel;
		this.operation = IntegrationOperation.POST_SEND;
		this.sent = sent;
	}
	
	public MessageContext(MessageChannel channel) {
		super();
		this.message = null;
		this.channel = channel;
		this.operation = IntegrationOperation.PRE_RECEIVE;
		this.sent = false;
	}

	public Message<?> getMessage() {
		return message;
	}

	public MessageChannel getChannel() {
		return channel;
	}

	public IntegrationOperation getOperation() {
		return operation;
	}

	public boolean isSent() {
		return sent;
	}
}
