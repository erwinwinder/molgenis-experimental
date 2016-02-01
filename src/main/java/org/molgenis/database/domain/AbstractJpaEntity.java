package org.molgenis.database.domain;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

@MappedSuperclass
@Cache(type = CacheType.SOFT // Cache everything until the JVM decides memory
// is low.
)
public abstract class AbstractJpaEntity {

	@Id
	private String id = "id" + UUID.randomUUID().toString().replace("-", "");

	@Version
	private Long version;

	public String getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractJpaEntity other = (AbstractJpaEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
