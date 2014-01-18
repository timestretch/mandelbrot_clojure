Mandelbrot Set in Clojure
-------------------------

Here are two different implementations of a simple Mandelbrot set fractal generator in Clojure.

mandelbrot.clj
--------------
This is the first version I did, which doesn't do anything fancy. It isn't particularly fast, but it is a bit more readable. This is pretty much just a straight forward port from an earlier C version I did long ago.

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
  

mandelbrot-pmap.clj
-------------------

This version uses pmap to concurrently calculate multiple rows at the same time. It runs much faster. The main mandelbrot function where the iterations occur has also been refactored to use only let bindings instead of def.

	(defn mandelbrot [cr ci]
	  (loop [i 0 zr 0.0 zi 0.0]
		(let [temp (float (* zr zi))
		  zr2 (float (* zr zr))
		  zi2 (float (* zi zi))]
		  (if (or (> (+ zi2 zr2) bailout) (>= i max_iterations))
		i ;return iterations
		(recur (inc i) (+ (- zr2 zi2) cr) (+ temp temp ci))))))

License
-------

License: Public Domain

Erik Wrenholt 2014-01-18
