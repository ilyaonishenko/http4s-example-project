package com.example.quickstart

import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

class HelloWorldService[F[_]: Effect] extends Http4sDsl[F] {

  val service: HttpService[F] = {
    HttpService[F] {
      case GET -> Root / "hello" / name =>
        Ok(s"Hello, $name!")
    }
  }
}
