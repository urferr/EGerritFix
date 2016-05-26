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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.eclipse.egerrit.internal.core.command.GetContentCommand;
import org.eclipse.egerrit.internal.core.exception.EGerritException;
import org.junit.Test;

public class GetContentCommandTest extends CommandTestWithSimpleReview {
	/**
	 * Test method for {@link org.eclipse.egerrit.core.command.GerritCommand#call()}.
	 *
	 * @throws Exception
	 */
	@Test
	public void testCall() throws Exception {
		GetContentCommand command = fGerrit.getContent(change_id, commit_id, filename);
		try {
			String result = command.call();
			assertEquals(fileContent, StringUtils.newStringUtf8(Base64.decodeBase64(result)));
		} catch (EGerritException e) {
			fail(e.getMessage());
		}
	}

}
