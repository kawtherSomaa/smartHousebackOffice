import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILivingRoom } from 'app/shared/model/living-room.model';

type EntityResponseType = HttpResponse<ILivingRoom>;
type EntityArrayResponseType = HttpResponse<ILivingRoom[]>;

@Injectable({ providedIn: 'root' })
export class LivingRoomService {
  public resourceUrl = SERVER_API_URL + 'api/living-rooms';

  constructor(protected http: HttpClient) {}

  create(livingRoom: ILivingRoom): Observable<EntityResponseType> {
    return this.http.post<ILivingRoom>(this.resourceUrl, livingRoom, { observe: 'response' });
  }

  update(livingRoom: ILivingRoom): Observable<EntityResponseType> {
    return this.http.put<ILivingRoom>(this.resourceUrl, livingRoom, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILivingRoom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILivingRoom[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
