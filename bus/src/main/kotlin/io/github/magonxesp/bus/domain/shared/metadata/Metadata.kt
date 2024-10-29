package io.github.magonxesp.bus.domain.shared.metadata

import java.io.Serializable

typealias MetadataValue = Serializable?
typealias MutableMetadata = MutableMap<String, MetadataValue>
typealias Metadata = Map<String, MetadataValue>
