package com.example.quickstart

import cats.effect.Effect
import org.http4s.{EntityDecoder, Uri}
import org.http4s.client.Client
import org.http4s.circe._

class SpaceXClient[F[_]: Effect](httpClient: Client[F]) {

  implicit val launchInfoEntityDecoder: EntityDecoder[F, LaunchInfo] = jsonOf[F, LaunchInfo]

  def getLaunchInfo(launchNum: Int): F[LaunchInfo] = {
    val finalUri: Uri = Uri.uri("https://api.spacexdata.com").withPath(s"/v3/launches/$launchNum")

    httpClient.expect[LaunchInfo](finalUri)
  }
}
