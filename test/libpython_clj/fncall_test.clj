(ns libpython-clj.fncall-test
  (:require [libpython-clj.python :as py]
            [clojure.test :refer :all]))


(py/initialize!)


(py/import-as testcode testmod)
(py/from-import inspect getfullargspec)

(deftest complex-fn-test
  (let [testmod (py/import-module "testcode")
        testcases (py/$. testmod complex_fn_testcases)]
    (is (= (-> (get testcases "complex_fn(1, 2, c=10, d=10, e=10)")
               (py/->jvm))
           (-> (py/$a testmod complex_fn 1 2 :c 10 :d 10 :e 10)
               (py/->jvm))))
    (is (= (-> (get testcases "complex_fn(1, 2, 10, 11, 12, d=10, e=10)")
               (py/->jvm))
           (-> (py/$a testmod complex_fn 1 2 10 11 12 :d 10 :e 10)
               (py/->jvm))))

    (is (= (-> (get testcases "complex_fn(1, 2, c=10, d=10, e=10)")
               (py/->jvm))
           (-> (apply py/afn testmod "complex_fn" [1 2 :c 10 :d 10 :e 10])
               (py/->jvm))))
    (is (= (-> (get testcases "complex_fn(1, 2, 10, 11, 12, d=10, e=10)")
               (py/->jvm))
           (-> (apply py/afn testmod "complex_fn" [1 2 10 11 12 :d 10 :e 10])
               (py/->jvm))))))
