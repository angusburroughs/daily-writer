(ns daily-writer.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [daily-writer.utils :as util]))

(defn get-fps-handler [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Pew pew!"})

(defn db-save-page [userId text dateOfEntry]
  (println-str "STUB: saving page for user=[" userId "] with dateOfEntry=[" dateOfEntry "], and text=[" text "]")
  (if "true"
    "success"
    "error"))

(defn map-to-response-body [result]
  (cond
    (= "success" result)
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (util/build-json-msg "meesa saved da ting")}
    :default
    {:status  500
     :headers {"Content-Type" "application/json"}
     :body    (util/build-json-msg "oopsie wopsie fucky wucky")}))

(defn handle-save-page [request]

  ; userId, text, dateOfEntry

  (let [body (:body request)
        {userId      :userId
         text        :text
         dateOfEntry :dateOfEntry} body]
    (map-to-response-body (db-save-page userId text dateOfEntry))))

; all routes for the app live here, split these up one day maybe
(defroutes app-routes
           (POST "/pages/save" [] handle-save-page)
           (route/not-found "You Must Be New Here"))

(defn -main                                                 ;(4)
  "This is our app's entry point"
  [& args]
  ; todo rely on some kind of config file for the port
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (server/run-server #'app-routes {:port port})
    (println (str "Running daily-writer webserver at http:/127.0.0.1:" port "/"))))
