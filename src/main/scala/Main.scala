import breeze.linalg._
import scalaglm.Lm
//import scalaglm._
import breeze.linalg._
import breeze.numerics._
import breeze.stats.distributions._
import breeze.stats.distributions.Rand.VariableSeed.randBasis
object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")

    val url = "http://archive.ics.uci.edu/ml/machine-learning-databases/00291/airfoil_self_noise.dat"
    val fileName = "input/self-noise.csv"

    // download the file to disk if it hasn't been already
    val file = new java.io.File(fileName)
    if (!file.exists) {
      val s = new java.io.PrintWriter(file)
      val data = scala.io.Source.fromURL(url).getLines
      data.foreach(l => s.write(l.trim.
        split('\t').filter(_ != "").
        mkString("", ",", "\n")))
      s.close
    }
    val mat = csvread(new java.io.File(fileName))
//    println("Dim: " + mat.rows + " " + mat.cols)
//    val figp = Utils.pairs(mat, List("Freq", "Angle", "Chord", "Velo", "Thick", "Sound"))
//    val y = mat(::, 5) // response is the final column
//    val X = mat(::, 0 to 4)
    val X = DenseMatrix.tabulate(100, 3)((i, j) =>
      Gaussian(j, j + 1).sample())
    val y = DenseVector.tabulate(100)(i =>
      Gaussian(2.0 + 1.5 * X(i, 0) + 0.5 * X(i, 1), 3.0).sample())
//    val mod = Lm(y, X, List("Freq", "Angle", "Chord", "Velo", "Thick"))
    val lm = Lm(y, X, List("V1", "V2", "V3"))
  }
}