import {Component, EventEmitter, Output} from '@angular/core';
import {StoryService} from '../../../../../../services/services/story.service';
import {StoryRequest} from '../../../../../../services/models/story-request';
import {AddOrUpdateStory$Params} from '../../../../../../services/fn/story/add-or-update-story';

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

  cover: Blob | null = null;

  constructor(private storyService: StoryService) {}

  // Handle file selection with proper typing
  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.cover = input.files[0];
      this.errorMsg = []; // Clear error when file is selected
    } else {
      this.cover = null;
      this.errorMsg = ['Please select a file'];
    }
  }

  saveStory() {
    if (!this.cover) {
      this.errorMsg = ['Please select a file before submitting'];
      return;
    }
    console.log('Story Request:', this.storyRequest); // Log the storyRequest object
    console.log('Cover file:', this.cover); // Check if cover file is selected
    const params: AddOrUpdateStory$Params = {
      body: {
        request: this.storyRequest,
        file: this.cover
      }
    };
    this.storyService.addOrUpdateStory(params).subscribe({
      next: () => {
        this.closeModal.emit();
        this.showSuccess.emit();
      },
      error: (err) => {
        console.error(err.error);
        this.errorMsg = err.error;
      }
    });
  }

  closeError(){
    this.errorMsg = [];
  }

  close() {
    this.closeModal.emit();
  }

}
