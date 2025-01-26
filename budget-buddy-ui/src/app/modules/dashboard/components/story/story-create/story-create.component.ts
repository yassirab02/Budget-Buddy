import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-story-create',
  templateUrl: './story-create.component.html',
  styleUrl: './story-create.component.css'
})
export class StoryCreateComponent {
  @Output() closeModal = new EventEmitter<EventEmitter<any>>();

  close() {
    this.closeModal.emit();
  }
}
