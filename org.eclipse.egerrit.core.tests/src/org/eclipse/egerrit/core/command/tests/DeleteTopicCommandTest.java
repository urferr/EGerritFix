/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command.tests;

import static org.junit.Assert.fail;

import org.eclipse.egerrit.internal.core.command.DeleteTopicCommand;
import org.eclipse.egerrit.internal.core.command.QueryChangesCommand;
import org.eclipse.egerrit.internal.core.command.SetTopicCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.eclipse.egerrit.internal.core.rest.TopicInput;
import org.eclipse.egerrit.internal.model.ChangeInfo;
import org.junit.Test;

/**
 * Test suite for {@link org.eclipse.egerrit.internal.core.command.SetTopicCommand}
 *
 * @since 1.0
 */
@SuppressWarnings("nls")
public class DeleteTopicCommandTest extends CommandTestWithSimpleReview {
	@Test
	public void testCall() {
		// set a topic
		final String TOPIC = "DELETETOPIC";
		SetTopicCommand command = fGerrit.setTopic(change_id);
		TopicInput topicInput = new TopicInput();
		topicInput.setTopic(TOPIC);
		command.setCommandInput(topicInput);

		try {
			command.call();

			DeleteTopicCommand deleteCommand = fGerrit.deleteTopic(change_id);
			deleteCommand.call();

			QueryChangesCommand queryForChange = fGerrit.queryChanges();
			queryForChange.addTopic("DELETETOPIC");
			queryForChange.setMaxNumberOfResults(1);

			//Make sure that the list of the retrieved reviews does not contain the changeinfo.
			ChangeInfo[] retrievedChangeInfo = queryForChange.call();
			for (ChangeInfo changeInfo : retrievedChangeInfo) {
				if (change_id.equals(changeInfo.getChange_id())) {
					fail();
				}
			}
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}
}
