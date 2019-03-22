package com.example.quickstart

import cats.effect.Effect
import org.http4s.Uri
import org.http4s.client.Client

class SpaceXClient[F[_]: Effect](httpClient: Client[F]) {

  def getLaunchInfo(launchNum: Int): F[String] = {
    val finalUri: Uri = Uri.uri("https://api.spacexdata.com").withPath(s"/v3/launches/$launchNum")

    httpClient.expect[String](finalUri)
  }
}
