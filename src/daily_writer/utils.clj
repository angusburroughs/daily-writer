(ns daily-writer.utils)

(defn build-json-msg [msg]
  (str "{'message':'" msg "'}"))
