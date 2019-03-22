package com.example.quickstart

import cats.effect.{Effect, IO}
import fs2.StreamApp
import org.http4s.client.blaze.Http1Client
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext

object HelloWorldServer extends StreamApp[IO] {
  import scala.concurrent.ExecutionContext.Implicits.global

  def stream(args: List[String], requestShutdown: IO[Unit]) = ServerStream.stream[IO]
}

object ServerStream {

  def stream[F[_]: Effect](implicit ec: ExecutionContext) =
    for {
      client <- Http1Client.stream[F]()
      service = new HelloWorldService[F](client)
      exitCode <- BlazeBuilder[F]
          .bindHttp(8080, "0.0.0.0")
          .mountService(service.service, "/")
          .serve
    } yield exitCode
}
