package framework.adapters.output.h2.data

import jakarta.persistence.*
import org.eclipse.persistence.annotations.Convert
import org.eclipse.persistence.annotations.Converter
import java.util.*

@Entity
@Table(name = "network")
@Converter(name = "uuidConverter", converterClass = UuidConverter::class)
class NetworkData(
  @Column(name = "switch_id") @Convert("uuidConverter") val switchId: UUID,
  @Embedded
  @AttributeOverride(name = "address", column = Column(name = "ip_address"))
  @AttributeOverride(name = "protocol", column = Column(name = "ip_protocol"))
  val ip: IPData,
  @Column(name = "name") val name: String,
  @Column(name = "cidr") val cidr: Int,
  @Id @Column(name = "id") val id: Int? = null
)