package fr.formation.afpa.domain;
// Generated 23 mai 2021 13:43:00 by Hibernate Tools 5.1.10.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TicketsId generated by hbm2java
 */
@Embeddable
public class TicketsId implements java.io.Serializable {

	private int id;
	private int languageLibraryId;

	public TicketsId() {
	}

	public TicketsId(int id, int languageLibraryId) {
		this.id = id;
		this.languageLibraryId = languageLibraryId;
	}

	@Column(name = "ID", nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "language_library_id", nullable = false)
	public int getLanguageLibraryId() {
		return this.languageLibraryId;
	}

	public void setLanguageLibraryId(int languageLibraryId) {
		this.languageLibraryId = languageLibraryId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TicketsId))
			return false;
		TicketsId castOther = (TicketsId) other;

		return (this.getId() == castOther.getId()) && (this.getLanguageLibraryId() == castOther.getLanguageLibraryId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getId();
		result = 37 * result + this.getLanguageLibraryId();
		return result;
	}

}
