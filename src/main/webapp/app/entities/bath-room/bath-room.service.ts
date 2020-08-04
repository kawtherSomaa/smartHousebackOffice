import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBathRoom } from 'app/shared/model/bath-room.model';

type EntityResponseType = HttpResponse<IBathRoom>;
type EntityArrayResponseType = HttpResponse<IBathRoom[]>;

@Injectable({ providedIn: 'root' })
export class BathRoomService {
  public resourceUrl = SERVER_API_URL + 'api/bath-rooms';

  constructor(protected http: HttpClient) {}

  create(bathRoom: IBathRoom): Observable<EntityResponseType> {
    return this.http.post<IBathRoom>(this.resourceUrl, bathRoom, { observe: 'response' });
  }

  update(bathRoom: IBathRoom): Observable<EntityResponseType> {
    return this.http.put<IBathRoom>(this.resourceUrl, bathRoom, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBathRoom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBathRoom[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
