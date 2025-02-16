import {Component, OnInit} from '@angular/core';
import {StoryService} from '../../../../../services/services/story.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ReportResponse} from '../../../../../services/models/report-response';
import {StoryResponse} from '../../../../../services/models/story-response';
import {GetReportById$Params} from '../../../../../services/fn/report/get-report-by-id';
import {FindStoryById$Params} from '../../../../../services/fn/story/find-story-by-id';

@Component({
  selector: 'app-story-detail',
  templateUrl: './story-detail.component.html',
  styleUrl: './story-detail.component.css'
})
export class StoryDetailComponent implements OnInit{

  storyResponse: StoryResponse = {};
  isLoading=true;

  constructor(private storyService: StoryService,
              private route: ActivatedRoute
  ) {
  }
  ngOnInit() {
    const storyId = this.route.snapshot.paramMap.get('id');
    if (storyId) {
      this.findStoryById(Number(storyId));
    }
  }

  findStoryById(storyId: number) {
    const params: FindStoryById$Params = { 'story-id': storyId };
    this.storyService.findStoryById(params).subscribe({
      next: (story: StoryResponse) => {
        this.storyResponse = story;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching report details:', err);
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

  get formattedContent(): string|undefined {
    return this.storyResponse?.content?.replace(/\n/g, '<br>');
  }
}
