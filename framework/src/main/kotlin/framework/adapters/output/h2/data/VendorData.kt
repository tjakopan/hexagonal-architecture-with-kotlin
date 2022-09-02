package framework.adapters.output.h2.data

import jakarta.persistence.Embeddable

@Suppress("JpaObjectClassSignatureInspection")
@Embeddable
enum class VendorData {
  CISCO,
  NETGEAR,
  HP,
  TPLINK,
  DLINK,
  JUNIPER
}