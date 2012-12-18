(ns scad-clj.designs.cage
  (:use [scad-clj.scad]))

(write-scad
 "/home/mfarrell/things/clj/cage.scad"

 (fn! 100)
 (let [n 3
       radius 10
       pillar 2
       gap 0.1]

   (defn rot [n r p obj]
     (concat
      (map #(rotate [(* %1 (/ 360 n)) [0 0 1]]
                    (translate [0 r 0]
                               obj))
           (range n))
      ))

   (defn base-hull [n r p h]
     (hull
      (rot n r p (cylinder p h))
      )
     )

   (defn base [n r p g]
     (difference
      (base-hull n (+ r p g) p  p)
      (base-hull n (+ r (- p) g) p (* 4 p))))
   
   (union
    (translate [0 0 (/ pillar 2)]
               (union
                (base n radius pillar gap)
                (translate [0 0 (* 2 radius)]
                           (base n radius pillar gap))
                (translate [0 0 radius]
                           (rot n (+ radius pillar gap) pillar
                                (cylinder pillar (* 2 radius))))
                ))
    (translate [0 0 radius]
               (sphere radius))
    )
   )
 )
 

