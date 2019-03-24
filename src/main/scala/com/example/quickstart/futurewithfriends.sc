
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

val f: Future[Unit] = Future(println("hello world from Future!"))

Future.sequence(List(f,f,f,f))

import cats.effect.IO
import cats.implicits._

val io: IO[Unit] = IO(println("hello world from IO"))

val iosList = List(io, io, io, io).sequence

iosList.unsafeRunSync()
