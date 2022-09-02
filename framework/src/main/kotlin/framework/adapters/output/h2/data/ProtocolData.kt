package framework.adapters.output.h2.data

import jakarta.persistence.Embeddable

@Suppress("JpaObjectClassSignatureInspection")
@Embeddable
enum class ProtocolData {
  IPV4, IPV6
}