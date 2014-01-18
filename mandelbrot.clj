; Clojure Mandelbrot set generator - Erik Wrenholt - 2013-01-27
; License: Public Domain

(def ITERATIONS 1000)
(def BAILOUT 16.0)

(defn mandelbrot [xx yy]
  (def bail 0.0)
  (def cr (- yy 0.5))
  (def ci xx)
  (def zi 0.0)
  (def zr 0.0)
  (def ii 0)
  (loop [i 0]
    (when (< i ITERATIONS)

      (def temp (* zr zi))
      (def zr2 (* zr zr))
      (def zi2 (* zi zi))
      (def zr (+ (- zr2 zi2) cr))
      (def zi (+ temp temp ci))
      
      (def ii i)
      (if (< (+ zi2 zr2) BAILOUT) (recur (inc i)))))
  (inc ii))

(doseq [y (range -39 39)]
    (doseq [x (range -39 39)]
      (if (= (mandelbrot (/ x 40.0) (/ y 40.0)) ITERATIONS)
	(print "*")
	(print " ")))
    (println))

