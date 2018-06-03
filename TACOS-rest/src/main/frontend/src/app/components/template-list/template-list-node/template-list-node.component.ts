import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Template} from "../../../services/template/template.service";

@Component({
  selector: 'app-template-list-node',
  templateUrl: './template-list-node.component.html',
  styleUrls: ['./template-list-node.component.css']
})
export class TemplateListNodeComponent implements OnInit {

  @Input()
  public template:Template;


  @Output()
  deleteAction : EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  public deleteTemplate() {
    this.deleteAction.emit(null);
  }

}
