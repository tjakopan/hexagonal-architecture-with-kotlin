package domain.vo

import java.util.*

@JvmInline
value class Id private constructor(val id: UUID) {
  constructor(id: String) : this(UUID.fromString(id))

  constructor() : this(UUID.randomUUID())
}