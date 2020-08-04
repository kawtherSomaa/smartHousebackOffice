import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoor } from 'app/shared/model/door.model';

type EntityResponseType = HttpResponse<IDoor>;
type EntityArrayResponseType = HttpResponse<IDoor[]>;

@Injectable({ providedIn: 'root' })
export class DoorService {
  public resourceUrl = SERVER_API_URL + 'api/doors';

  constructor(protected http: HttpClient) {}

  create(door: IDoor): Observable<EntityResponseType> {
    return this.http.post<IDoor>(this.resourceUrl, door, { observe: 'response' });
  }

  update(door: IDoor): Observable<EntityResponseType> {
    return this.http.put<IDoor>(this.resourceUrl, door, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
