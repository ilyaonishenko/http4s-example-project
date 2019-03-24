package com.example.quickstart

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class Message(msg: String)

object Message {
  implicit val messageEncoder: Encoder[Message] = deriveEncoder
  implicit val messageDecoder: Decoder[Message] = deriveDecoder
}
