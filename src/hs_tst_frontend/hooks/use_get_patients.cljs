(ns hs-tst-frontend.hooks.use-get-patients
  (:require [helix.hooks :as hooks]))

(defn fetch-data []
  (-> (js/fetch "http://localhost:8000/patient")
      (.then (fn [response]
               (.json response)))
      (.then (fn [data]
               (js->clj data :keywordize-keys true)))))

(defn use-get-patients []
  (let [[data set-data] (hooks/use-state nil)
        [is-loading? set-is-loading] (hooks/use-state true)
        [is-success? set-is-success] (hooks/use-state false)
        [is-error? set-is-error] (hooks/use-state false)]
    (hooks/use-effect
      :once
      (do
        (set-is-loading true)
        (set-is-error false)
        (-> (fetch-data)
            (.then (fn [data]
                     (set-data data)
                     (set-is-success true)))
            (.catch (fn [_error]
                      (set-is-error true)))
            (.finally (fn []
                        (set-is-loading false))))))

    {:data data
     :is-success? is-success?
     :is-loading? is-loading?
     :is-error? is-error?}))
