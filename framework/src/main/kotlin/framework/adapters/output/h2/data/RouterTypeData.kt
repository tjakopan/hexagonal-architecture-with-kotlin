package framework.adapters.output.h2.data

import jakarta.persistence.Embeddable

@Suppress("JpaObjectClassSignatureInspection")
@Embeddable
enum class RouterTypeData {
  EDGE,
  CORE
}