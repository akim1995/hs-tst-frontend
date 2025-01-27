(ns hs-tst-frontend.patient-http-requests)

(defn post-data [data]
  (js/fetch "http://localhost:8000/patient"
            (clj->js {:method "POST"
                      :headers {"Content-Type" "application/json"}
                      :body (js/JSON.stringify (clj->js data))})))

(defn put-data [data patient-id]
  (js/fetch (str "http://localhost:8000/patient/"  patient-id)
            (clj->js {:method "PUT"
                      :headers {"Content-Type" "application/json"}
                      :body (js/JSON.stringify (clj->js data))})))

(defn delete-patient [patient-id]
  (js/fetch (str "http://localhost:8000/patient/" patient-id)
            (clj->js {:method "DELETE"})))
