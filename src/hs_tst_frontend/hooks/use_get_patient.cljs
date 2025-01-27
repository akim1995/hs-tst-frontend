(ns hs-tst-frontend.hooks.use-get-patient
  (:require
   [helix.hooks :as hooks]))

(defn fetch-data [patient-id]
  (-> (js/fetch (str "http://localhost:8000/patient/" patient-id))
      (.then (fn [response]
               (.json response)))
      (.then (fn [data]
               (js->clj data :keywordize-keys true)))))

(defn use-get-patient [patient-id]
  (let [[data set-data] (hooks/use-state nil)
        [is-loading? set-is-loading] (hooks/use-state true)
        [is-success? set-is-success] (hooks/use-state false)
        [is-error? set-is-error] (hooks/use-state false)
        load-data (fn []
                    (set-is-loading true)
                    (set-is-error false)
                    (-> (fetch-data patient-id)
                        (.then (fn [data]
                                 (set-data data)
                                 (set-is-success true)))
                        (.catch (fn [_error]
                                  (set-is-error true)))
                        (.finally (fn []
                                    (set-is-loading false)))))]
    (hooks/use-effect
      [patient-id]
      (load-data))
    {:data data
     :refetch-data load-data
     :is-success? is-success?
     :is-loading? is-loading?
     :is-error? is-error?}))
