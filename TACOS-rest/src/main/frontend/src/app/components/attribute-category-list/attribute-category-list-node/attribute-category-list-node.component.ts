import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AttributeCategory} from "../../../services/attributeCategory/attribute-category.service";

@Component({
  selector: 'app-attribute-category-list-node',
  templateUrl: './attribute-category-list-node.component.html',
  styleUrls: ['./attribute-category-list-node.component.css']
})
export class AttributeCategoryListNodeComponent implements OnInit {

  @Input()
  public attributeCategory: AttributeCategory;

  @Output()
  deleteAction : EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  public deleteAttributeCategory() {
    this.deleteAction.emit(null);
  }
}
