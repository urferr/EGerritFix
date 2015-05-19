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
 *     Francois Chouinard - Rebased on GerritQueryCommand and removed useless code
 *******************************************************************************/

package org.eclipse.egerrit.core.command;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.egerrit.core.EGerritCorePlugin;
import org.eclipse.egerrit.core.GerritRepository;

/**
 * The <a href=
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#get-content"
 * >Get
 * Content</a> command. It returns a <a href=
 * "http://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#String"
 * ></a> base64 encoded.
 * <p>
 *
 * @since 1.0
 */
public class GetContentCommand extends QueryCommand<String> {

    // ------------------------------------------------------------------------
    // Attributes
    // ------------------------------------------------------------------------

	private String fChange_id;

	private String fRevision;

	private String fTargetFile;

    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------

    /**
     * The constructor
     *
     * @param gerritRepository   the gerrit repository
     * @param id the change-id
     * @param revision revisions-id
     * @param targetFile the file to fetch
     */
    public GetContentCommand(GerritRepository gerritRepository, String id, String revision, String targetFile) {
        super(gerritRepository, String.class);
        this.setId(id);
        this.setRevision(revision);
        this.settargetFile(targetFile);


    }

    private void settargetFile(String targetFile) {
		this.fTargetFile = targetFile;

	}

    private String gettargetFile() {
		return fTargetFile;

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


	// ------------------------------------------------------------------------
	// Format the query
	// ------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see org.eclipse.egerrit.core.command.GerritCommand#formatRequest()
     */
    @Override
    public HttpRequestBase formatRequest() {

        // Get the generic URI
        URIBuilder uriBuilder = getRepository().getURIBuilder(fAuthIsRequired);

        URI uri = null;
        try {
            String file = URLEncoder.encode(gettargetFile(), "UTF-8");
            // Set the path
            String path = new StringBuilder(uriBuilder.getPath())
                    .append("/changes/").append(getId()) //$NON-NLS-1$
                    .append("/revisions/").append(getRevision())//$NON-NLS-1$
                    .append("/files/").append(file) //$NON-NLS-1$
                    .append("/content") //$NON-NLS-1$
                    .toString();
            uriBuilder.setPath(path);
            uri = new URI(URIUtil.toUnencodedString(uriBuilder.build()));
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            EGerritCorePlugin.logError("URI syntax exception", e); //$NON-NLS-1$
        }

        return new HttpGet(uri);
    }

}
