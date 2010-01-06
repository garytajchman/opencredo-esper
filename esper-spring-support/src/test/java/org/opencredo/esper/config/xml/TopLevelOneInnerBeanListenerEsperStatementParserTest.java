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

package org.opencredo.esper.config.xml;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.EsperStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.espertech.esper.client.UpdateListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TopLevelOneInnerBeanListenerEsperStatementParserTest {

	@Autowired
	EsperStatement statement;
	
	@Test
	public void testInnerBeanListenerRegistered() {
		assertNotNull(statement);
		assertEquals(EsperTestConstants.EPL, statement.getEPL());
		
		Set<UpdateListener> listeners = statement.getListeners();
		assertEquals(1, listeners.size());
	}
}
