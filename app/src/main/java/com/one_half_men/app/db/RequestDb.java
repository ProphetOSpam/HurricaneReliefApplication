package com.one_half_men.app.db;

// needs `Request` to be implemented first
// tracking: https://github.com/ProphetOSpam/HurricaneReliefApplication/issues/11
public class RequestDb {
}
// public class RequestDb extends Database<Request> implements Searchable<Request, RequestQuery> {
// 	public RequestDb(Map<Key<Request>, Request> entries) {
// 		super(entries);
// 	}
//
// 	@Override
// 	public Set<Key<Request>> search(RequestQuery query) {
// 		Set<Key<Request>> result = new HashSet<Key<Request>>();
//          for (Key<Request> key : entries.keySet()) {
//              Request request = entries.get(key);
//              if (query.equals(request)) {
//                  result.add(key);
//              }
//          }
//      return result;
// 	}
// }
