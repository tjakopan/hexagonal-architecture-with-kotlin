package domain.vo

data class Location(
  val address: String? = null,
  val city: String? = null,
  val state: String? = null,
  val zipCode: Int? = null,
  val country: String? = null,
  val latitude: Float? = null,
  val longitude: Float? = null
)
