; Clojure Mandelbrot set generator - Erik Wrenholt - 2013-01-27
; License: Public Domain

(def max_iterations 1000)
(def bailout 16.0)

; Returns number of iterations at point
(defn mandelbrot [cr ci]
  (loop [i 0 zr 0.0 zi 0.0]
    (let [temp (float (* zr zi))
	  zr2 (float (* zr zr))
	  zi2 (float (* zi zi))]
      (if (or (> (+ zi2 zr2) bailout) (>= i max_iterations))
	i ;return iterations
	(recur (inc i) (+ (- zr2 zi2) cr) (+ temp temp ci))))))

(def ramp [" " "." ":" ";" "!" "+" "E" "$" "#" "&" "%"])

; Return which character in ramp above to return for fractal.
(defn char-at-vec [v]
  (ramp (mod (mandelbrot (- (/ (last v) 40.0) 0.5) (/ (first v) 40.0)) 11)))

; Returns a sequence of vectors which are passed to calc-row
(defn row-vec-pair [row]
  (map vector (range -39 39) (take 78 (repeat row))))

; Calculate iterations for an entire row.
(defn calc-row [row_seq]
  (str (apply str (map char-at-vec row_seq)) "\n"))

; Use parallel map calculate concurrently render an entire row.
(time (println (apply str (pmap calc-row (map row-vec-pair (range -39 39))))))

(shutdown-agents)
