import {Component, OnInit} from '@angular/core';
import {StoryService} from '../../../../../services/services/story.service';
import {PageResponseStoryResponse} from '../../../../../services/models/page-response-story-response';
import {StoryResponse} from '../../../../../services/models/story-response';
import {Toggle$Params} from '../../../../../services/fn/story/toggle';
import {DeleteStory$Params} from '../../../../../services/fn/story/delete-story';
import {Router} from '@angular/router';
import {animate, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-story',
  templateUrl: './story.component.html',
  styleUrl: './story.component.css',
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('300ms', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class StoryComponent implements OnInit {
  isLoading = true;
  createStory = false
  page = 0;
  size = 5;
  pages: any = [];
  message = '';
  level: 'success' | 'error' = 'success';
  errorMsg: Array<string> = [];
  storyPageResponse: PageResponseStoryResponse = {};  // Store the actual story
  myStories: PageResponseStoryResponse = {};  // Store the actual story
  storyResponse: StoryResponse = {};
  activeTab = "all";
  isDelete=false;
  storyToDelete: any;
  showSuccess=false;




  toggleCreate() {
    this.createStory = !this.createStory
  }

  constructor(
    private storyService: StoryService,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.findAllStories();
    this.findAllStoriesByOwner();
  }

  findAllStories(resetPage: boolean = false) {
    if (resetPage) {
      this.page = 0; // Reset to the first page
    }
    this.storyService.findAllDisplayableStories({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (stories) => {
          // Store the backend response in budgetResponse
          this.storyPageResponse = stories;
          // Create an array of page numbers for pagination
          this.pages = Array(this.storyPageResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching stories:', err);
          this.message = 'An error occurred while fetching the stories.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }

  findAllStoriesByOwner(resetPage: boolean = false) {
    if (resetPage) {
      this.page = 0; // Reset to the first page
    }
    this.storyService.findAllStoriesByOwner({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (stories) => {
          // Store the backend response in budgetResponse
          this.myStories = stories;
          // Create an array of page numbers for pagination
          this.pages = Array(this.storyPageResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error fetching stories:', err);
          this.message = 'An error occurred while fetching the stories.';
          this.level = 'error';
          this.isLoading = false;
        }
      });
  }

  // Inside your component class
  getInitials(owner: string | undefined): string {
    if (!owner) {
      return ''; // Handle undefined or empty owner
    }

    const parts = owner.trim().split(' '); // Split the string into parts by spaces
    const firstNameInitial = parts[0]?.charAt(0).toUpperCase() || '';
    const lastNameInitial = parts[1]?.charAt(0).toUpperCase() || ''; // Use the second part if it exists
    return firstNameInitial + lastNameInitial;
  }

  toggleReaction(event: Event,storyId: number | undefined, reactionType: "LIKE"): void {
    event.stopPropagation();
    const id = storyId ?? 0;
    const params: Toggle$Params = { 'story-id': id, 'reactionType': reactionType };

    this.storyService.toggle(params).subscribe({
      next: (response: StoryResponse) => {
        this.storyResponse = response;
      },
      error: (err) => {
        console.error('Error fetching stories:', err);
        this.message = 'An error occurred while fetching the stories.';
        this.level = 'error';
      }
    });
  }

  deleteStory(id: number | undefined) {
    if (id === undefined) {
      console.error('Story ID is undefined');
      return;
    }
    const params: DeleteStory$Params = { 'story-id': id };
    this.storyService.deleteStory(params)
      .subscribe({
        next: (response) => {
          this.findAllStoriesByOwner();
          this.isDelete = false;
        },
        error: (err) => {
          console.error('Error deleting story:', err);
          this.message = 'An error occurred while deleting the story.';
          this.level = 'error';
        }
      });
  }

  openDelete(id:any) {
    this.storyToDelete = id;
    this.isDelete = true;
  }

  viewStory(storyId: number | undefined) {
    this.router.navigate(['/story', storyId]);
  }

  handleSuccess(): void {
    this.showSuccess = true;
    setTimeout(() => {
      this.findAllStoriesByOwner();
      this.showSuccess = false;
    }, 3000);
  }
}
