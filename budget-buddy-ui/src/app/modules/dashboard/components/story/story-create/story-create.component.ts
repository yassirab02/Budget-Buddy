import {Component, EventEmitter, Output} from '@angular/core';
import {StoryService} from '../../../../../services/services/story.service';
import {StoryRequest} from '../../../../../services/models/story-request';

@Component({
  selector: 'app-story-create',
  templateUrl: './story-create.component.html',
  styleUrl: './story-create.component.css'
})
export class StoryCreateComponent {
  @Output() closeModal = new EventEmitter<EventEmitter<any>>();
  @Output() showSuccess = new EventEmitter<void>();
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: string[] = [];
  storyRequest:StoryRequest = {
    title: '',
    description: '',
    content: '',
    archived: false,
    status: 'PUBLISHED',
  }

  constructor(private storyService:StoryService) {}

  close() {
    this.closeModal.emit();
  }
  saveStory() {
    this.storyService.addOrUpdateStory({
      body: this.storyRequest
    }).subscribe({
      next: () => {
        this.closeModal.emit();
        this.showSuccess.emit();
      },
      error: (err) => {
        console.error(err.error);
        this.errorMsg = err.error.validationErrors || [];
      }
    });
  }

}
