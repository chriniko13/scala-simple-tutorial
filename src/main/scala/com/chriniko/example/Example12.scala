package com.chriniko.example

/*
  Higher Order Functions (https://docs.scala-lang.org/tour/higher-order-functions.html)
 */
class Example12 extends Example {

  override def run(): Unit = {


    // 1
    val salaries = Seq(1000, 2000, 3000)
    val doubleUp: Function[Int, Int] = (x: Int) => x * 2
    salaries.map(doubleUp).foreach(println)
    println

    salaries.map(x => x * 2).foreach(println)
    println

    salaries.map(_ * 2).foreach(println)
    println

    // 2
    val runsInMiles = Seq(4.5, 7.8, 8, 3, 12)
    val runnerApp = RunnerApp(runsInMiles)
    println(s"runsInMiles = $runsInMiles")
    println(s"runsInKms = ${runnerApp.milesToKms()}")


    // 3
    println
    val sals = List(1.087, 1.450, 1.300)
    println(SalaryRaiser.findNewSalaries(sals, SalaryRaiser.smallPromotion(sals)))
    println(SalaryRaiser.findNewSalaries(sals, SalaryRaiser.bigPromotion(sals)))
    println(SalaryRaiser.findNewSalaries(sals, SalaryRaiser.hugePromotion(sals)))


    // 4
    println

    def urlBuilder(ssl: Boolean, domainName: String): (String, String) => String = {
      val schema = if (ssl) "https://" else "http://"
      (endpoint: String, query: String) => s"$schema$domainName/$endpoint?$query"
    }

    val baseUrl = urlBuilder(ssl = true, "www.example.com")
    println(baseUrl("students", "name=chriniko"))
  }


  //--------------------------------------------------------------------------
  case class RunnerApp(runsInMiles: Seq[Double]) {

    private def milesToKms(miles: Double) = miles * 1.6D

    def milesToKms(): Seq[Double] =

      runsInMiles
        .map(runInMiles => milesToKms(runInMiles))
        .collect {
          case item: Double if item > 0 => item
        }


  }

  //---------------------------------------------------------------------------
  object SalaryRaiser {

    private def promotion(salaries: List[Double], promotionFactor: Double => Double): List[Double] =
      salaries.map(promotionFactor)

    def smallPromotion(salaries: List[Double]): List[Double] =
      promotion(salaries, salary => salary * math.log(salary))

    def bigPromotion(salaries: List[Double]): List[Double] =
      promotion(salaries, salary => salary * 1.1)

    def hugePromotion(salaries: List[Double]): List[Double] =
      promotion(salaries, salary => salary * salary)


    def findNewSalaries(salaries: List[Double], promotions: List[Double]): List[Double] = {
      promotions.zip(salaries).map(t => t._1 + t._2)
    }

  }


}
