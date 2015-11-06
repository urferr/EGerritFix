/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.egerrit.core.command.QueryChangesCommand;
import org.eclipse.egerrit.core.command.SetTopicCommand;
import org.eclipse.egerrit.core.exception.EGerritException;
import org.eclipse.egerrit.core.rest.TopicInput;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.core.command.SetTopicCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class SetTopicCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		// set a topic
		SetTopicCommand command = fGerrit.setTopic(change_id);
		TopicInput topicInput = new TopicInput();
		topicInput.setTopic("ABC");

		command.setCommandInput(topicInput);

		String result = null;
		try {
			result = command.call();
			QueryChangesCommand queryForChange = fGerrit.queryChanges();
			queryForChange.addTopic("ABC");
			queryForChange.setMaxNumberOfResults(1);
			assertEquals(change_id, queryForChange.call()[0].getId());
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
		assertEquals(result, topicInput.getTopic());

	}
}
