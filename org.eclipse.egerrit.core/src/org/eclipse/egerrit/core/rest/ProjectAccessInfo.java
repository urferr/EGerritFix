/*******************************************************************************
 * Copyright (c) 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jacques Bouthillier - Initial API and implementation
 *******************************************************************************/

package org.eclipse.egerrit.core.rest;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;

//import org.eclipse.egerrit.core.gerrit.AccessSectionInfo;
import com.google.gerrit.common.data.ProjectInfo;

/**
 * The <a href=
 * "https://git.eclipse.org/r/Documentation/rest-api-access.html#project-access-info"
 * >ProjectAccessInfo</a> entity contains information about the project access.
 * <p>
 * This structure is filled by GSON when parsing the corresponding JSON
 * structure in an HTTP response.
 *
 * @since 1.0
 */
public class ProjectAccessInfo {

	// ------------------------------------------------------------------------
	// The data structure
	// ------------------------------------------------------------------------
	// used to fire events of registered properties
	private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	// The revision of the refs/meta/config branch from which the access rights
	// were loaded.
	private String revision;

	// The parent project from which permissions are inherited as a ProjectInfo
	// entity.
	// not set for the All-Project project
	private ProjectInfo inherits_from;

	// AccessSectionInfo inf;
	// // The local access rights of the project as a map that maps the refs to
	// // AccessSectionInfo entities.
	// private Map<String, AccessSectionInfo> local;

	// Whether the calling user owns this project.
	// not set if false
	private Boolean is_owner = false;

	// The subject of the commit (header line of the commit message).
	private Set<String> ownerOf;

	// Whether the calling user can upload to any ref.
	// not set if false
	private Boolean can_upload = false;

	// Whether the calling user can add any ref.
	// not set if false
	private Boolean can_add = false;

	// Whether the calling user can see the refs/meta/config branch of the
	// project.
	// not set if false
	private Boolean config_visible = false;

	// ------------------------------------------------------------------------
	// The getters
	// ------------------------------------------------------------------------

	// public void setCommitter(GitPersonInfo committer) {
	// firePropertyChange("committer", this.committer,
	// this.committer = committer);
	// }

	/**
	 * @return the revision
	 */
	public String getRevision() {
		return revision;
	}

	/**
	 * @param revision
	 *            the revision to set
	 */
	public void setRevision(String revision) {
		firePropertyChange("revision", this.revision, this.revision = revision);
	}

	/**
	 * @return the inherits_from
	 */
	public ProjectInfo getInherits_from() {
		return inherits_from;
	}

	/**
	 * @param inherits_from
	 *            the inherits_from to set
	 */
	public void setInherits_from(ProjectInfo inherits_from) {
		firePropertyChange("inherits_from", this.inherits_from,
				this.inherits_from = inherits_from);
	}

	// /**
	// * @return the local
	// */
	// public Map<String, AccessSectionInfo> getLocal() {
	// return local;
	// }
	//
	// /**
	// * @param local
	// * the local to set
	// */
	// public void setLocal(Map<String, AccessSectionInfo> local) {
	// firePropertyChange("local", this.local, this.local = local);
	// }

	/**
	 * @return the is_owner
	 */
	public Boolean getIs_owner() {
		return is_owner;
	}

	/**
	 * @param is_owner
	 *            the is_owner to set
	 */
	public void setIs_owner(Boolean is_owner) {
		firePropertyChange("is_owner", this.is_owner, this.is_owner = is_owner);
	}

	/**
	 * @return the ownerOf
	 */
	public Set<String> getOwnerOf() {
		return ownerOf;
	}

	/**
	 * @param ownerOf
	 *            the ownerOf to set
	 */
	public void setOwnerOf(Set<String> ownerOf) {
		firePropertyChange("ownerOf", this.ownerOf, this.ownerOf = ownerOf);
	}

	/**
	 * @return the can_upload
	 */
	public Boolean getCan_upload() {
		return can_upload;
	}

	/**
	 * @param can_upload
	 *            the can_upload to set
	 */
	public void setCan_upload(Boolean can_upload) {
		firePropertyChange("can_upload", this.can_upload,
				this.can_upload = can_upload);
	}

	/**
	 * @return the can_add
	 */
	public Boolean getCan_add() {
		return can_add;
	}

	/**
	 * @param can_add
	 *            the can_add to set
	 */
	public void setCan_add(Boolean can_add) {
		firePropertyChange("can_add", this.can_add, this.can_add = can_add);
	}

	/**
	 * @return the config_visible
	 */
	public Boolean getConfig_visible() {
		return config_visible;
	}

	/**
	 * @param config_visible
	 *            the config_visible to set
	 */
	public void setConfig_visible(Boolean config_visible) {
		firePropertyChange("config_visible", this.config_visible,
				this.config_visible = config_visible);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}

	// ------------------------------------------------------------------------
	// Object
	// ------------------------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((can_add == null) ? 0 : can_add.hashCode());
		result = prime * result
				+ ((can_upload == null) ? 0 : can_upload.hashCode());
		result = prime * result
				+ ((config_visible == null) ? 0 : config_visible.hashCode());
		result = prime * result
				+ ((is_owner == null) ? 0 : is_owner.hashCode());
		result = prime * result + ((ownerOf == null) ? 0 : ownerOf.hashCode());
		result = prime * result
				+ ((revision == null) ? 0 : revision.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProjectAccessInfo other = (ProjectAccessInfo) obj;
		if (can_add == null) {
			if (other.can_add != null) {
				return false;
			}
		} else if (!can_add.equals(other.can_add)) {
			return false;
		}
		if (can_upload == null) {
			if (other.can_upload != null) {
				return false;
			}
		} else if (!can_upload.equals(other.can_upload)) {
			return false;
		}
		if (config_visible == null) {
			if (other.config_visible != null) {
				return false;
			}
		} else if (!config_visible.equals(other.config_visible)) {
			return false;
		}
		if (is_owner == null) {
			if (other.is_owner != null) {
				return false;
			}
		} else if (!is_owner.equals(other.is_owner)) {
			return false;
		}
		if (ownerOf == null) {
			if (other.ownerOf != null) {
				return false;
			}
		} else if (!ownerOf.equals(other.ownerOf)) {
			return false;
		}
		if (revision == null) {
			if (other.revision != null) {
				return false;
			}
		} else if (!revision.equals(other.revision)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProjectAccessInfo [revision=" + revision + ", is_owner="
				+ is_owner + ", ownerOf=" + ownerOf + ", can_upload="
				+ can_upload + ", can_add=" + can_add + ", config_visible="
				+ config_visible + "]";
	}

}
