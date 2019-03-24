package com.example.quickstart

import cats.effect.Effect
import org.http4s.{EntityDecoder, EntityEncoder, HttpService}
import org.http4s.dsl.Http4sDsl
import cats.implicits._
import org.http4s.circe._

class HelloWorldService[F[_]: Effect](spaceXClient: SpaceXClient[F]) extends Http4sDsl[F] {

  implicit val launchInfoEntityEncoder: EntityEncoder[F, LaunchInfo] = jsonEncoderOf[F, LaunchInfo]
  implicit val stringsEntityEncoder: EntityEncoder[F, List[String]] = jsonEncoderOf[F, List[String]]

  implicit val messageEntityEncoder: EntityEncoder[F, Message] = jsonEncoderOf[F, Message]
  implicit val messageEntityDecoder: EntityDecoder[F, Message] = jsonOf[F, Message]

  val service: HttpService[F] = {
    HttpService[F] {
      case GET -> Root / "hello" / name =>
        Ok(s"Hello, $name!")
      case GET -> Root / "spacex_launches" / IntVar(launchNum) =>
        spaceXClient.getLaunchInfo(launchNum).flatMap(launchInfo => Ok(launchInfo.links.flickr_images))
      case req @ POST -> Root / "print" =>
        for {
          message <- req.as[Message]
          resp <- Ok(Message(s"${message.msg} was handled"))
        } yield {
          println(s"${message.msg}")
          resp
        }
    }
  }
}
