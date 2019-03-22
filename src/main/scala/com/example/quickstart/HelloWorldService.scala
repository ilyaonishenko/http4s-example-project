package com.example.quickstart

import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import cats.implicits._
import org.http4s.client.Client

class HelloWorldService[F[_]: Effect](httpClient: Client[F]) extends Http4sDsl[F] {

  val spaceXClient = new SpaceXClient[F](httpClient)

  val service: HttpService[F] = {
    HttpService[F] {
      case GET -> Root / "hello" / name =>
        Ok(s"Hello, $name!")
      case GET -> Root / "curiosity" / IntVar(launchNum) =>
        spaceXClient.getLaunchInfo(launchNum).flatMap(launchInfo =>
          Ok(launchInfo.split("flickr_images\":").filter(_.contains("https://farm8.staticflickr.com")).head))
    }
  }
}
