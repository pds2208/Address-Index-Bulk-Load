package producer

trait SendMessage {
  def send(items: List[AddressLine])
}
