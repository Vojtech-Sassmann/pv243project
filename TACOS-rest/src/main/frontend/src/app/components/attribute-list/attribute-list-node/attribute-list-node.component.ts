import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Attribute} from "../../../services/attribute/attribute.service";

@Component({
  selector: 'app-attribute-list-node',
  templateUrl: './attribute-list-node.component.html',
  styleUrls: ['./attribute-list-node.component.css']
})
export class AttributeListNodeComponent implements OnInit {

  @Input()
  public attribute: Attribute;

  @Output()
  deleteAction : EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  public deleteAttribute() {
    this.deleteAction.emit(null);
  }

}
