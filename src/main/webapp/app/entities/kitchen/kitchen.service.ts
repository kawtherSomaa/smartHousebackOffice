import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKitchen } from 'app/shared/model/kitchen.model';

type EntityResponseType = HttpResponse<IKitchen>;
type EntityArrayResponseType = HttpResponse<IKitchen[]>;

@Injectable({ providedIn: 'root' })
export class KitchenService {
  public resourceUrl = SERVER_API_URL + 'api/kitchens';

  constructor(protected http: HttpClient) {}

  create(kitchen: IKitchen): Observable<EntityResponseType> {
    return this.http.post<IKitchen>(this.resourceUrl, kitchen, { observe: 'response' });
  }

  update(kitchen: IKitchen): Observable<EntityResponseType> {
    return this.http.put<IKitchen>(this.resourceUrl, kitchen, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKitchen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKitchen[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
