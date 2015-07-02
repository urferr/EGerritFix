/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Guy Perron - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;
import org.eclipse.egerrit.core.rest.CommentInfo;

/**
 * The command: POST /changes/link:#change-id[\{change-id\}]/revisions/link:#revision-id[\{revision-id\}]/publish/
 * <p>
 *
 * @since 1.0
 */
public class PublishDraftRevisionCommand extends PostCommand<String> {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	private String fChange_id;

	private String fRevision;

	private CommentInfo fCommentInfo = new CommentInfo();

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 *
	 * @param gerritRepository
	 *            the gerrit repository
	 * @param id
	 *            the change-id
	 * @param revision
	 *            revisions-id
	 */
	public PublishDraftRevisionCommand(GerritRepository gerritRepository, String id, String revision) {
		super(gerritRepository, String.class);
		this.setId(id);
		this.setRevision(revision);
		requiresAuthentication(true);
	}

	private void setRevision(String revision) {
		fRevision = revision;

	}

	private String getRevision() {
		return fRevision;

	}

	public String getId() {
		return fChange_id;
	}

	public void setId(String change_id) {
		this.fChange_id = change_id;
	}

	public void setCommentInfo(CommentInfo commentInfo) {
		fCommentInfo = commentInfo;
	}

	// ------------------------------------------------------------------------
	// Format the query
	// ------------------------------------------------------------------------

	/*	 * (non-Javadoc)
	 *
	 * @see org.eclipse.egerrit.core.command.GerritCommand#formatRequest()
	 */
	@Override
	public HttpRequestBase formatRequest() {

		// Get the generic URI
		URIBuilder uriBuilder = getRepository().getURIBuilder(fAuthIsRequired);

		URI uri = null;
		try {
			// Set the path
			String path = new StringBuilder(uriBuilder.getPath()).append("/changes/") //$NON-NLS-1$
					.append(getId())
					.append("/revisions/").append(getRevision())//$NON-NLS-1$
					.append("/publish") //$NON-NLS-1$
					.toString();
			uriBuilder.setPath(path);
			uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
		} catch (URISyntaxException e) {
			EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
		}

		HttpPost httpPost = new HttpPost(uri);

//		JsonObject jsonCommentInput = new JsonObject();
//
////		jsonCommentInput.addProperty("author", fCommentInfo.getAuthor());
//		jsonCommentInput.addProperty("id", fCommentInfo.getId());
//		jsonCommentInput.addProperty("inReplyTo", fCommentInfo.getInReplyTo());
//		jsonCommentInput.addProperty("kind", fCommentInfo.getKind());
//		jsonCommentInput.addProperty("line", fCommentInfo.getLine());
//		jsonCommentInput.addProperty("message", fCommentInfo.getMessage());
//		jsonCommentInput.addProperty("patch_set", fCommentInfo.getPatch_set());
//		jsonCommentInput.addProperty("path", fCommentInfo.getPath());
////		jsonCommentInput.addProperty("range", fCommentInfo.getRange());
//		jsonCommentInput.addProperty("side", fCommentInfo.getSide());
//		jsonCommentInput.addProperty("updated", fCommentInfo.getUpdated());
//
//		StringEntity input = null;
//		try {
//			input = new StringEntity(jsonCommentInput.toString());
//		} catch (UnsupportedEncodingException e) {
//			EGerritCorePlugin.logError("URI error encoding CommentInfo", e); //$NON-NLS-1$
//
//		}
//
//		input.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, GerritCommand.JSON_HEADER));
//		httpPut.setEntity(input);

		return httpPost;
	}
}
